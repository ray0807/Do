package carbon.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.List;

import carbon.Carbon;
import carbon.R;
import carbon.animation.AnimUtils;
import carbon.animation.AnimatedView;

/**
 * Created by Marcin on 2015-01-07.
 */
public class Snackbar extends FrameLayout implements AnimatedView {
    public static int INFINITE = -1;
    private float prevX, swipe;
    private ValueAnimator animator;

    public interface OnDismissedListener {
        void onDismissed();
    }

    private TextView message;
    private Button button;
    private Style style;
    private long duration;
    private Runnable hideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    private Handler handler;
    private View content;
    OnDismissedListener onDismissedListener;
    boolean swipeToDismiss = true, tapOutsideToDismiss = true;

    static List<Snackbar> next = new ArrayList<>();

    public enum Style {
        Floating, Docked
    }

    public Snackbar(Context context) {
        super(context);
        init(null, R.attr.carbon_snackbarStyle);
    }

    public Snackbar(Context context, String message, String action, int duration) {
        super(context);
        init(null, R.attr.carbon_snackbarStyle);
        setMessage(message);
        setAction(action);
        setDuration(duration);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        content = inflate(getContext(), R.layout.carbon_snackbar, null);
        addView(content);

        message = (TextView) findViewById(R.id.carbon_messageText);
        button = (Button) findViewById(R.id.carbon_actionButton);

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.Snackbar, defStyleAttr, 0);
        style = Style.values()[a.getInt(R.styleable.Snackbar_carbon_layoutStyle, 0)];
        setStyle(style);
        Carbon.initAnimations(this, attrs, defStyleAttr);

        duration = a.getInt(R.styleable.Snackbar_carbon_duration, 0);

        a.recycle();

        handler = new Handler();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    public void show() {
        if (this.getParent() != null)
            return;
        synchronized (Snackbar.class) {
            if (!next.contains(this))
                next.add(this);
            if (next.indexOf(this) == 0) {
                View decor = ((Activity) getContext()).getWindow().getDecorView();
                ((ViewGroup) decor.findViewById(android.R.id.content)).addView(this,
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                ViewHelper.setAlpha(content, 0);
                AnimUtils.animateIn(content, getInAnimation(), null);
                if (duration != INFINITE)
                    handler.postDelayed(hideRunnable, duration);
            }
        }
    }

    public static void clearQueue() {
        next.clear();
    }

    public void hide() {
        synchronized (Snackbar.class) {
            if (getParent() == null)
                return;
            handler.removeCallbacks(hideRunnable);
            if (onDismissedListener != null)
                onDismissedListener.onDismissed();
            AnimUtils.animateOut(content, getOutAnimation(), new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animator) {
                    hideInternal();
                }
            });
        }
    }

    private void hideInternal() {
        synchronized (Snackbar.class) {
            if (getParent() != null)
                ((ViewGroup) getParent()).removeView(this);
            if (next.contains(this))
                next.remove(this);
            if (next.size() != 0)
                next.get(0).show();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean hit = event.getY() > content.getTop() && getParent() != null;
        if (tapOutsideToDismiss && !hit) {
            hide();
            return false;
        }

        if (hit && swipeToDismiss) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                swipe = 0;
                prevX = event.getX();
                handler.removeCallbacks(hideRunnable);
                if (animator != null) {
                    animator.cancel();
                    swipe = ViewHelper.getTranslationX(content);
                }
                super.onTouchEvent(event);
                return true;
            } else if (event.getAction() == MotionEvent.ACTION_MOVE && animator == null) {
                swipe += event.getX() - prevX;
                prevX = event.getX();
                ViewHelper.setTranslationX(content, swipe);
                ViewHelper.setAlpha(content, 1 - Math.abs(swipe) / content.getWidth());
                postInvalidate();
                if (Math.abs(swipe) > content.getWidth() / 3) {
                    handler.removeCallbacks(hideRunnable);
                    animator = ObjectAnimator.ofFloat(swipe, content.getWidth() * Math.signum(swipe));
                    animator.setDuration(200);
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float s = (Float) animation.getAnimatedValue();
                            ViewHelper.setTranslationX(content, s);
                            ViewHelper.setAlpha(content, 1 - Math.abs(s) / content.getWidth());
                            postInvalidate();
                        }
                    });
                    animator.start();
                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            hideInternal();
                            animator = null;
                        }
                    });
                }
                return true;
            } else if (animator == null) {
                animator = ObjectAnimator.ofFloat(swipe, 0);
                animator.setDuration(200);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float s = (Float) animation.getAnimatedValue();
                        ViewHelper.setTranslationX(content, s);
                        ViewHelper.setAlpha(content, 1 - Math.abs(s) / content.getWidth());
                        postInvalidate();
                    }
                });
                animator.start();
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        animator = null;
                        if (duration != INFINITE)
                            handler.postDelayed(hideRunnable, duration);
                    }
                });
                super.onTouchEvent(event);
                return true;
            }
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        button.setOnClickListener(l);
    }

    public void setAction(String action) {
        if (action != null) {
            button.setText(action);
            button.setVisibility(View.VISIBLE);
            content.setPadding(content.getPaddingLeft(), 0, (int) getResources().getDimension(R.dimen.carbon_paddingHalf), 0);
        } else {
            content.setPadding(content.getPaddingLeft(), 0, content.getPaddingLeft(), 0);
            button.setVisibility(View.GONE);
        }
    }

    public String getAction() {
        return button.getText().toString();
    }

    public void setMessage(String message) {
        this.message.setText(message);
    }

    public String getMessage() {
        return message.getText().toString();
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) content.getLayoutParams();
        if (style == Style.Floating) {
            layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            int margin = (int) getResources().getDimension(R.dimen.carbon_padding);
            layoutParams.setMargins(margin, margin, margin, margin);
            layoutParams.gravity = Gravity.LEFT | Gravity.BOTTOM;
        } else {
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            layoutParams.setMargins(0, 0, 0, 0);
            layoutParams.gravity = Gravity.BOTTOM;
        }
        content.setLayoutParams(layoutParams);
        requestLayout();
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public boolean isSwipeToDismissEnabled() {
        return swipeToDismiss;
    }

    public void setSwipeToDismissEnabled(boolean swipeToDismiss) {
        this.swipeToDismiss = swipeToDismiss;
    }

    public boolean isTapOutsideToDismissEnabled() {
        return tapOutsideToDismiss;
    }

    public void setTapOutsideToDismissEnabled(boolean tapOutsideToDismiss) {
        this.tapOutsideToDismiss = tapOutsideToDismiss;
    }

    public void setOnDismissedListener(OnDismissedListener onDismissedListener) {
        this.onDismissedListener = onDismissedListener;
    }
}

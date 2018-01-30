package com.vita.animation.itemanimator;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewPropertyAnimator;

/**
 * @FileName: com.vita.animation.itemanimator.ScaleItemAnimator.java
 * @Author: Vita
 * @Date: 2018-01-30 09:22
 * @Usage:
 */
public class ScaleItemAnimator extends BaseItemAnimator {

    @Override
    public void setRemoveAnimator(RecyclerView.ViewHolder holder, ViewPropertyAnimator animator) {
        View itemView = holder.itemView;
        itemView.setPivotX(itemView.getWidth() / 2);
        itemView.setPivotY(itemView.getHeight() / 2);
        animator.scaleX(0).scaleY(0);
    }

    @Override
    public void onRemoveAnimatorEnd(RecyclerView.ViewHolder holder) {
        View itemView = holder.itemView;
        itemView.setScaleX(1);
        itemView.setScaleY(1);
    }

    @Override
    public void addAnimInit(RecyclerView.ViewHolder holder) {
        View itemView = holder.itemView;
        itemView.setScaleX(0);
        itemView.setScaleY(0);
    }

    @Override
    public void setAddAnimator(RecyclerView.ViewHolder holder, ViewPropertyAnimator animator) {
        View itemView = holder.itemView;
        itemView.setPivotX(itemView.getWidth() / 2);
        itemView.setPivotY(itemView.getHeight() / 2);
        animator.scaleX(1).scaleY(1);
    }

    @Override
    public void addAnimatorCancel(RecyclerView.ViewHolder holder) {
        View itemView = holder.itemView;
        itemView.setScaleX(1);
        itemView.setScaleY(1);
    }

    @Override
    public void setOldChangeAnimator(RecyclerView.ViewHolder holder, ViewPropertyAnimator animator) {
        View itemView = holder.itemView;
        itemView.setPivotX(itemView.getWidth() / 2);
        itemView.setPivotY(itemView.getHeight() / 2);
        animator.scaleX(0).scaleY(0);
    }

    @Override
    public void onOldChangeAnimatorEnd(RecyclerView.ViewHolder holder) {
        View itemView = holder.itemView;
        itemView.setScaleX(1);
        itemView.setScaleY(1);
    }

    @Override
    public void newChangeAnimatorInit(RecyclerView.ViewHolder holder) {
        View itemView = holder.itemView;
        itemView.setScaleX(0);
        itemView.setScaleY(0);
    }

    @Override
    public void setNewChangeAnimator(RecyclerView.ViewHolder holder, ViewPropertyAnimator animator) {
        View itemView = holder.itemView;
        itemView.setPivotX(itemView.getWidth() / 2);
        itemView.setPivotY(itemView.getHeight() / 2);
        animator.scaleX(1).scaleY(1);
    }

    @Override
    public void onNewChangeAnimatorEnd(RecyclerView.ViewHolder holder) {
        View itemView = holder.itemView;
        itemView.setScaleX(1);
        itemView.setScaleY(1);
    }
}

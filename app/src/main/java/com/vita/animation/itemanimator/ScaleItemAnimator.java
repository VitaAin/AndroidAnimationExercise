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
    public void setRemoveAnim(RecyclerView.ViewHolder holder, ViewPropertyAnimator animator) {
        View itemView = holder.itemView;
        itemView.setPivotX(itemView.getWidth() / 2);
        itemView.setPivotY(itemView.getHeight() / 2);
        animator.scaleX(0).scaleY(0);
    }

    @Override
    public void removeAnimEnd(RecyclerView.ViewHolder holder) {
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
    public void setAddAnim(RecyclerView.ViewHolder holder, ViewPropertyAnimator animator) {
        View itemView = holder.itemView;
        itemView.setPivotX(itemView.getWidth() / 2);
        itemView.setPivotY(itemView.getHeight() / 2);
        animator.scaleX(1).scaleY(1);
    }

    @Override
    public void addAnimCancel(RecyclerView.ViewHolder holder) {
        View itemView = holder.itemView;
        itemView.setScaleX(1);
        itemView.setScaleY(1);
    }

    @Override
    public void setOldChangeAnim(RecyclerView.ViewHolder holder, ViewPropertyAnimator animator) {
        View itemView = holder.itemView;
        itemView.setPivotX(itemView.getWidth() / 2);
        itemView.setPivotY(itemView.getHeight() / 2);
        animator.scaleX(0).scaleY(0);
    }

    @Override
    public void oldChangeAnimEnd(RecyclerView.ViewHolder holder) {
        View itemView = holder.itemView;
        itemView.setScaleX(1);
        itemView.setScaleY(1);
    }

    @Override
    public void newChangeAnimInit(RecyclerView.ViewHolder holder) {
        View itemView = holder.itemView;
        itemView.setScaleX(0);
        itemView.setScaleY(0);
    }

    @Override
    public void setNewChangeAnim(RecyclerView.ViewHolder holder, ViewPropertyAnimator animator) {
        View itemView = holder.itemView;
        itemView.setPivotX(itemView.getWidth() / 2);
        itemView.setPivotY(itemView.getHeight() / 2);
        animator.scaleX(1).scaleY(1);
    }

    @Override
    public void newChangeAnimEnd(RecyclerView.ViewHolder holder) {
        View itemView = holder.itemView;
        itemView.setScaleX(1);
        itemView.setScaleY(1);
    }
}

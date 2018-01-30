package com.vita.animation.itemanimator;

import android.support.v7.widget.RecyclerView;
import android.view.ViewPropertyAnimator;

/**
 * @FileName: com.vita.animation.itemanimator.RotateItemAnimator.java
 * @Author: Vita
 * @Date: 2018-01-30 09:59
 * @Usage:
 */
public class RotateItemAnimator extends BaseItemAnimator {

    @Override
    public void setRemoveAnimator(RecyclerView.ViewHolder holder, ViewPropertyAnimator animator) {
        holder.itemView.setPivotX(0);
        holder.itemView.setPivotY(holder.itemView.getHeight() / 2);
        animator.rotationY(90);
    }

    @Override
    public void onRemoveAnimatorEnd(RecyclerView.ViewHolder holder) {
        holder.itemView.setRotationY(0);
    }

    @Override
    public void addAnimatorInit(RecyclerView.ViewHolder holder) {
        holder.itemView.setPivotX(holder.itemView.getWidth());
        holder.itemView.setPivotY(holder.itemView.getHeight() / 2);
        holder.itemView.setRotationY(-90);
    }

    @Override
    public void setAddAnimator(RecyclerView.ViewHolder holder, ViewPropertyAnimator animator) {
        animator.rotationY(0);
    }

    @Override
    public void addAnimatorCancel(RecyclerView.ViewHolder holder) {
        holder.itemView.setRotationY(0);
    }

    @Override
    public void setOldChangeAnimator(RecyclerView.ViewHolder holder, ViewPropertyAnimator animator) {
        holder.itemView.setPivotX(holder.itemView.getWidth() / 2);
        holder.itemView.setPivotY(holder.itemView.getHeight() / 2);
        animator.rotationY(90);
    }

    @Override
    public void onOldChangeAnimatorEnd(RecyclerView.ViewHolder holder) {
        holder.itemView.setRotationY(0);
    }

    @Override
    public void newChangeAnimatorInit(RecyclerView.ViewHolder holder) {
        holder.itemView.setPivotX(holder.itemView.getWidth() / 2);
        holder.itemView.setPivotY(holder.itemView.getHeight() / 2);
        holder.itemView.setRotationY(-90);
    }

    @Override
    public void setNewChangeAnimator(RecyclerView.ViewHolder holder, ViewPropertyAnimator animator) {
        animator.rotationY(0).setStartDelay(getChangeDuration());
    }

    @Override
    public void onNewChangeAnimatorEnd(RecyclerView.ViewHolder holder) {
        holder.itemView.setRotationY(0);
        holder.itemView.animate().setStartDelay(0);
    }
}

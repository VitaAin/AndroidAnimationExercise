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
    public void setRemoveAnim(RecyclerView.ViewHolder holder, ViewPropertyAnimator animator) {
        holder.itemView.setPivotX(0);
        holder.itemView.setPivotY(holder.itemView.getHeight() / 2);
        animator.rotationY(90);
    }

    @Override
    public void removeAnimEnd(RecyclerView.ViewHolder holder) {
        holder.itemView.setRotationY(0);
    }

    @Override
    public void addAnimInit(RecyclerView.ViewHolder holder) {
        holder.itemView.setPivotX(holder.itemView.getWidth());
        holder.itemView.setPivotY(holder.itemView.getHeight() / 2);
        holder.itemView.setRotationY(-90);
    }

    @Override
    public void setAddAnim(RecyclerView.ViewHolder holder, ViewPropertyAnimator animator) {
        animator.rotationY(0);
    }

    @Override
    public void addAnimCancel(RecyclerView.ViewHolder holder) {
        holder.itemView.setRotationY(0);
    }

    @Override
    public void setOldChangeAnim(RecyclerView.ViewHolder holder, ViewPropertyAnimator animator) {
        holder.itemView.setPivotX(holder.itemView.getWidth() / 2);
        holder.itemView.setPivotY(holder.itemView.getHeight() / 2);
        animator.rotationY(90);
    }

    @Override
    public void oldChangeAnimEnd(RecyclerView.ViewHolder holder) {
        holder.itemView.setRotationY(0);
    }

    @Override
    public void newChangeAnimInit(RecyclerView.ViewHolder holder) {
        holder.itemView.setPivotX(holder.itemView.getWidth() / 2);
        holder.itemView.setPivotY(holder.itemView.getHeight() / 2);
        holder.itemView.setRotationY(-90);
    }

    @Override
    public void setNewChangeAnim(RecyclerView.ViewHolder holder, ViewPropertyAnimator animator) {
        animator.rotationY(0).setStartDelay(getChangeDuration());
    }

    @Override
    public void newChangeAnimEnd(RecyclerView.ViewHolder holder) {
        holder.itemView.setRotationY(0);
        holder.itemView.animate().setStartDelay(0);
    }
}

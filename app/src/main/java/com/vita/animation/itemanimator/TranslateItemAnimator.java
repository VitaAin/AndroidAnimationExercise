package com.vita.animation.itemanimator;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewPropertyAnimator;

/**
 * @FileName: com.vita.animation.itemanimator.TranslateItemAnimator.java
 * @Author: Vita
 * @Date: 2018-01-30 09:44
 * @Usage:
 */
public class TranslateItemAnimator extends BaseItemAnimator {

    @Override
    public void setRemoveAnim(RecyclerView.ViewHolder holder, ViewPropertyAnimator animator) {
        animator.translationX(-holder.itemView.getWidth());
    }

    @Override
    public void removeAnimEnd(RecyclerView.ViewHolder holder) {
        holder.itemView.setTranslationX(0);
    }

    @Override
    public void addAnimInit(RecyclerView.ViewHolder holder) {
        holder.itemView.setTranslationX(-holder.itemView.getWidth());
    }

    @Override
    public void setAddAnim(RecyclerView.ViewHolder holder, ViewPropertyAnimator animator) {
        animator.translationX(0);
    }

    @Override
    public void addAnimCancel(RecyclerView.ViewHolder holder) {
        holder.itemView.setTranslationX(0);
    }

    @Override
    public void setOldChangeAnim(RecyclerView.ViewHolder holder, ViewPropertyAnimator animator) {
        animator.translationX(-holder.itemView.getWidth());
    }

    @Override
    public void oldChangeAnimEnd(RecyclerView.ViewHolder holder) {
        holder.itemView.setTranslationX(0);
    }

    @Override
    public void newChangeAnimInit(RecyclerView.ViewHolder holder) {
        holder.itemView.setTranslationX(holder.itemView.getWidth());
    }

    @Override
    public void setNewChangeAnim(RecyclerView.ViewHolder holder, ViewPropertyAnimator animator) {
        animator.translationX(0);
    }

    @Override
    public void newChangeAnimEnd(RecyclerView.ViewHolder holder) {
        holder.itemView.setTranslationX(0);
    }
}

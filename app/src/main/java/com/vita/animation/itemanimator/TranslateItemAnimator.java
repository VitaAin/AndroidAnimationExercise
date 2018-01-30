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
    public void setRemoveAnimator(RecyclerView.ViewHolder holder, ViewPropertyAnimator animator) {
        animator.translationX(-holder.itemView.getWidth());
    }

    @Override
    public void onRemoveAnimatorEnd(RecyclerView.ViewHolder holder) {
        holder.itemView.setTranslationX(0);
    }

    @Override
    public void addAnimInit(RecyclerView.ViewHolder holder) {
        holder.itemView.setTranslationX(-holder.itemView.getWidth());
    }

    @Override
    public void setAddAnimator(RecyclerView.ViewHolder holder, ViewPropertyAnimator animator) {
        animator.translationX(0);
    }

    @Override
    public void addAnimatorCancel(RecyclerView.ViewHolder holder) {
        holder.itemView.setTranslationX(0);
    }

    @Override
    public void setOldChangeAnimator(RecyclerView.ViewHolder holder, ViewPropertyAnimator animator) {
        animator.translationX(-holder.itemView.getWidth());
    }

    @Override
    public void onOldChangeAnimatorEnd(RecyclerView.ViewHolder holder) {
        holder.itemView.setTranslationX(0);
    }

    @Override
    public void newChangeAnimatorInit(RecyclerView.ViewHolder holder) {
        holder.itemView.setTranslationX(holder.itemView.getWidth());
    }

    @Override
    public void setNewChangeAnimator(RecyclerView.ViewHolder holder, ViewPropertyAnimator animator) {
        animator.translationX(0);
    }

    @Override
    public void onNewChangeAnimatorEnd(RecyclerView.ViewHolder holder) {
        holder.itemView.setTranslationX(0);
    }
}

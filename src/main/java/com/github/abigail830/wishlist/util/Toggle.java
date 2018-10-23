package com.github.abigail830.wishlist.util;

public enum Toggle {
    TEST_MODE(false);

    private boolean status;

    Toggle(boolean status) {
        this.status = status;
    }

    public boolean isON() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }



}

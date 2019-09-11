package com.shubhloans.shubhloans.model;

/**
 * Model class to override equals method to ignore case-sensitive case.
 */

public class Word {
    private String dataKey;

    public Word(String dataKey) {
        this.dataKey = dataKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Word)) return false;

        Word word = (Word) o;

        return dataKey.equalsIgnoreCase(word.dataKey);
    }

    @Override
    public int hashCode() {
        return dataKey.hashCode();
    }

    /**
     * Getter for data key.
     *
     * @return
     */
    public String getDataKey() {
        return dataKey;
    }

}

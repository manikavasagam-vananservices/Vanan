package com.vanancrm.Common;

public interface TranscriptionPrice {

    public double[] basePri = {1.00, 1.75, 2.00, 3.99};
    public double[] discountPri = {0.90, 0.80, 0.70};
    public double[] timecodePri = {0.25, 0.50};
    public double additionalQtyPri = 0.25;
    public double transcationPri = 0.05;
    public double verbatimPri = 0.25;
    public double[] offerPri = {0.10};
}

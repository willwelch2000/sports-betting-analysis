package com.personal.model.Strategies;

public class Strategy2 extends Strategy {
    
    public Strategy2() {
        super("Expected total greater than 0.02 and -120 <= odds <= 120 or 0.4 <= percentage <= 0.6", 0.4, 0.6, -120, 120, 0.02, true);
    }

}

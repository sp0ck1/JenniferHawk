package com.jenniferhawk.speedrunslive;

import java.util.List;


public class SRLRaceEntrantList {

//A list of SRL Race Result Entries
    private List<SRLRaceEntrant> entrants;

    public SRLRaceEntrantList(List<SRLRaceEntrant> srlRaceEntrantList) {
        setEntrants(srlRaceEntrantList);
    }

    public List<SRLRaceEntrant> getEntrants() {
        return entrants;
    }

    private void setEntrants(List<SRLRaceEntrant> entrants) {
        this.entrants = entrants;
    }


}

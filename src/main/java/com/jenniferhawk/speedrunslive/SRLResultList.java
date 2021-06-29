package com.jenniferhawk.speedrunslive;

import java.util.List;


public class SRLResultList {


    private List<SRLRaceResultEntry> entries;

    public SRLResultList(List<SRLRaceResultEntry> srlRaceResultEntryList) {
        setEntries(srlRaceResultEntryList);
    }

    public List<SRLRaceResultEntry> getEntries() {
        return entries;
    }

    private void setEntries(List<SRLRaceResultEntry> entries) {
        this.entries = entries;
    }


}

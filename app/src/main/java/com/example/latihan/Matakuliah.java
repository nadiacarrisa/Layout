package com.example.latihan;

public class Matakuliah {
    private String namamtk;
    private int sks;
    private String dosen;

    public Matakuliah(String namamtk, int sks, String dosen) {
        this.namamtk = namamtk;
        this.sks = sks;
        this.dosen = dosen;
    }

    public String getNamamtk() {
        return namamtk;
    }

    public void setNamamtk(String namamtk) {
        this.namamtk = namamtk;
    }

    public int getSks() {
        return sks;
    }

    public void setSks(int sks) {
        this.sks = sks;
    }

    public String getDosen() {
        return dosen;
    }

    public void setDosen(String dosen) {
        this.dosen = dosen;
    }

}

package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class Mult extends Thread{

    private HostBlacklistsDataSourceFacade skds;
    private AtomicInteger ocurrencesCount;
    private int BLACK_LIST_ALARM_COUNT;
    private AtomicInteger checkedListsCount;
    private String ipaddress;
    private LinkedList<Integer> blackListOcurrences;
    private int servidorActual;
    private int numeroAnalisis;

    public Mult(HostBlacklistsDataSourceFacade skds, AtomicInteger ocurrencesCount, int BLACK_LIST_ALARM_COUNT, AtomicInteger checkedListsCount, String ipaddress, LinkedList<Integer> blackListOcurrences, int servidorActual, int numeroAnalisis) {
        this.skds= skds;
        this.ocurrencesCount=ocurrencesCount;
        this.BLACK_LIST_ALARM_COUNT =BLACK_LIST_ALARM_COUNT;
        this.checkedListsCount = checkedListsCount;
        this.ipaddress =  ipaddress;
        this.blackListOcurrences = blackListOcurrences;
        this.servidorActual= servidorActual;
        this.numeroAnalisis= numeroAnalisis;
    }


    @Override
    public void run() {
        for(int i = servidorActual ; i < (servidorActual + numeroAnalisis) && ocurrencesCount.get() < BLACK_LIST_ALARM_COUNT ; i++){
            checkedListsCount.getAndIncrement();
            if (skds.isInBlackListServer(i, ipaddress)){
                blackListOcurrences.add(i);
                ocurrencesCount.getAndIncrement();
            }
        }
    }

}

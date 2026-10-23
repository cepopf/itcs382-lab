package com.bikeshare;

public class Ride {
    private final String memberId;
    private final MemberType memberType;
    private final Bike bike;
    private final String startStation;

    public Ride(String memberId, MemberType memberType, Bike bike, String startStation) {
        this.memberId = memberId;
        this.memberType = memberType;
        this.bike = bike;
        this.startStation = startStation;
    }

    public String getMemberId() {
        return memberId;
    }

    public MemberType getMemberType() {
        return memberType;
    }

    public Bike getBike() {
        return bike;
    }

    public int calculateFee(int minutes) {
        return memberType.calculateFee(minutes);
    }
}
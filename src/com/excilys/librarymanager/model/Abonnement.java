package com.excilys.librarymanager.model;
public enum Abonnement {
	BASIC(2),PREMIUM(5),VIP(20);
	public int quota;
	private  Abonnement(int quota){
		this.quota = quota;
		}
	
}


//public enum Abonnement {
//BASIC,PREMIUM,VIP
//}

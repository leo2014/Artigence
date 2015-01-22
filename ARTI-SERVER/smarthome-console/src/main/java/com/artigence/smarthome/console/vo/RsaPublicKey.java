package com.artigence.smarthome.console.vo;

public class RsaPublicKey {

	private String modulus;
	private String exponent;
	
	public RsaPublicKey(){}
	
	public RsaPublicKey(String modulus,String exponent){
		this.exponent = exponent;
		this.modulus = modulus;
	}
	
	public String getModulus() {
		return modulus;
	}
	public void setModulus(String modulus) {
		this.modulus = modulus;
	}
	public String getExponent() {
		return exponent;
	}
	public void setExponent(String exponent) {
		this.exponent = exponent;
	}
	
}

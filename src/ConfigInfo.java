
public class ConfigInfo {
	private final String publicKey;
	private final String privateKey;
	public ConfigInfo(String publicKey, String privateKey) {
		super();
		this.publicKey = publicKey;
		this.privateKey = privateKey;
	}

	public String getPublicKey() {
		return publicKey;
	}
	public String getPrivateKey() {
		return privateKey;
	}
	
}

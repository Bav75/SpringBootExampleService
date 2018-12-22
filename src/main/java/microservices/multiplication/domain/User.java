package microservices.multiplication.domain;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public final class User {
	
	private final String alias;
	
	public User(String alias) {
		this.alias = alias;
	}
	
	protected User() {
		alias = null;
	}

}

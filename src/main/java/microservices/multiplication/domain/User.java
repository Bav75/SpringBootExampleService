package microservices.multiplication.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
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

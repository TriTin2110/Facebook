package Enums;

public enum TypeAnnouce {
	FRIEND_REQUEST("FRIEND_REQUEST"), FRIEND_RESPONSE("FRIEND_RESPONSE"), FRIEND_ACCEPTED("FRIEND_ACCEPTED");

	private String message;

	TypeAnnouce(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}
}

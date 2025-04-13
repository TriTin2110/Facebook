package Enums;

public enum TypeAnnouce {
	FRIEND_REQUEST() {
		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return "Friend Request";
		}
	};

	public abstract String getName();
}

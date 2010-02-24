package org.eclipse.equinox.p2.engine;

public interface IProfileEvent {

	public static final byte ADDED = 0;
	public static final byte REMOVED = 1;
	public static final byte CHANGED = 2;

	public byte getReason();

	public String getProfileId();

}
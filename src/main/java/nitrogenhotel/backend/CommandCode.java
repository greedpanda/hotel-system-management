package nitrogenhotel.backend;

/**
 * Enumeration for Command.run() return value.
 * May encapsulate any necessary metadata if necessary in the future.
 */
public enum CommandCode {
  OK,
  ERR_SQL,
  ERR_GUI,
  ERR,
}

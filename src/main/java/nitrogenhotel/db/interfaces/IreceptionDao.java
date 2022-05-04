package nitrogenhotel.db.interfaces;

import nitrogenhotel.db.entries.User;

/**
 * An interface that defines methods specific for the receptionists.
 */
public interface IreceptionDao {

  void promoteUserToRecp(User us) throws Exception;

  void demoteUserFromRecp(User us) throws Exception;
}

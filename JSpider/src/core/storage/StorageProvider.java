package core.storage;

import core.util.config.PropertySet;
import core.storage.spi.StorageSPI;


/**
 *
 * $Id: StorageProvider.java,v 1.3 2003/04/11 16:37:05 vanrogu Exp $
 *
 * @author G�nther Van Roey
 */
public interface StorageProvider {

    public StorageSPI createStorage(PropertySet props);

}

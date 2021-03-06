package api.event.resource;

import api.model.EMailAddress;
import api.model.FetchedResource;
import api.event.EventVisitor;

/**
 * $Id: EMailAddressReferenceDiscoveredEvent.java,v 1.1 2003/04/08 15:50:25 vanrogu Exp $
 */
public class EMailAddressReferenceDiscoveredEvent extends ResourceRelatedEvent {

    protected EMailAddress eMailAddress;

    public EMailAddressReferenceDiscoveredEvent ( FetchedResource resource, EMailAddress emailAddress ) {
        super ( resource );
        this.eMailAddress = emailAddress;
    }

    public FetchedResource getResource ( ) {
        return (FetchedResource) resource;
    }

    public void accept(EventVisitor visitor) {
        visitor.visit(this);
    }

    public EMailAddress getEMailAddress ( ) {
        return eMailAddress;
    }

    public String getComment() {
        return "email address ref found in '" + resource.getURL() + "' : '" + eMailAddress.getAddress() + "'";
    }

    public String toString ( ) {
        return getComment();
    }

}

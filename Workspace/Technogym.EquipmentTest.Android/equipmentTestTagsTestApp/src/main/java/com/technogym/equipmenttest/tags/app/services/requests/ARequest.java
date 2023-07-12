package com.technogym.equipmenttest.tags.app.services.requests;

public abstract class ARequest implements IRequest {

	// { Internal Fields
	
    protected String _token = "";
    protected String _serviceURL = "";	
    
    // }

	// { Constructors

    /**
     * Constructor
     * @param token: session token
     */
    public ARequest(String token) {
        _token = token;
    }
    
    // }

	// { Overriding Methods
	
	@Override
	public String getServiceUrl() {
		return _serviceURL;
	}

    // }

}

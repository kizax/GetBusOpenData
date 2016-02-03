/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package getopendata;

/**
 *
 * @author kizax
 */
public class ProviderData {
    
        private final double providerId;
    private final String providerName;

    ProviderData(double providerId, String providerName) {
        this.providerId = providerId;
        this.providerName = providerName;
    }

    @Override
    public String toString() {
        String vdDataStr = String.format("%1$.1f, %2$s", providerId, providerName);
        return vdDataStr;
    }
    
}

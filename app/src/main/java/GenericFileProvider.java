import java.security.Provider;

/**
 * Created by alegralabs on 27/02/18.
 */

public class GenericFileProvider extends Provider {
    /**
     * Constructs a provider with the specified name, version number,
     * and information.
     *
     * @param name    the provider name.
     * @param version the provider version number.
     * @param info    a description of the provider and its services.
     */
    protected GenericFileProvider(String name, double version, String info) {
        super(name, version, info);
    }
}

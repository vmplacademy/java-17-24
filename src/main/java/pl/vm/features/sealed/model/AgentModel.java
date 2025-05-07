package pl.vm.features.sealed.model;

/**
 * Represents an AI Agent model.
 * AI Agents are autonomous systems that can perform tasks and make decisions based on their programming and learning.
 */
public final class AgentModel {
    private final String name;
    private final String version;
    private final String provider;
    private final String[] capabilities;
    private final boolean isAutonomous;

    public AgentModel(String name, String version, String provider, String[] capabilities, boolean isAutonomous) {
        this.name = name;
        this.version = version;
        this.provider = provider;
        this.capabilities = capabilities;
        this.isAutonomous = isAutonomous;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getProvider() {
        return provider;
    }

    public String[] getCapabilities() {
        return capabilities;
    }

    public boolean isAutonomous() {
        return isAutonomous;
    }

    @Override
    public String toString() {
        return "AgentModel{" +
                "name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", provider='" + provider + '\'' +
                ", capabilities=" + String.join(", ", capabilities) +
                ", isAutonomous=" + isAutonomous +
                '}';
    }
} 
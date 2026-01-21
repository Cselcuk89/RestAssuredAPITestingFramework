package com.testautomation.apitesting.utils;

/**
 * Environment configuration manager.
 * Supports multiple environments (dev, staging, prod) following industry best practices.
 */
public final class EnvironmentConfig {

    private EnvironmentConfig() {
        // Private constructor to prevent instantiation
    }

    /**
     * Supported environments
     */
    public enum Environment {
        DEV("dev"),
        STAGING("staging"),
        PROD("prod");

        private final String name;

        Environment(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static Environment fromString(String text) {
            for (Environment env : Environment.values()) {
                if (env.name.equalsIgnoreCase(text)) {
                    return env;
                }
            }
            return DEV; // Default to DEV
        }
    }

    private static Environment currentEnvironment;

    /**
     * Get current environment from system property or environment variable.
     */
    public static Environment getCurrentEnvironment() {
        if (currentEnvironment == null) {
            String env = System.getProperty("env", System.getenv().getOrDefault("TEST_ENV", "dev"));
            currentEnvironment = Environment.fromString(env);
        }
        return currentEnvironment;
    }

    /**
     * Set environment for testing purposes.
     */
    public static void setEnvironment(Environment environment) {
        currentEnvironment = environment;
    }

    /**
     * Get base URI based on current environment.
     * In production, this would read from environment-specific config files.
     */
    public static String getBaseUri() {
        String envSpecificKey = "base.uri." + getCurrentEnvironment().getName();
        String baseUri = PropertyUtils.getProperty(envSpecificKey);
        
        // Fall back to default base.uri if environment-specific one is not found
        if (baseUri == null || baseUri.isEmpty()) {
            baseUri = PropertyUtils.getProperty("base.uri");
        }
        
        return baseUri;
    }

    /**
     * Check if running in production environment.
     */
    public static boolean isProduction() {
        return getCurrentEnvironment() == Environment.PROD;
    }

    /**
     * Check if running in staging environment.
     */
    public static boolean isStaging() {
        return getCurrentEnvironment() == Environment.STAGING;
    }

    /**
     * Check if running in development environment.
     */
    public static boolean isDevelopment() {
        return getCurrentEnvironment() == Environment.DEV;
    }
}

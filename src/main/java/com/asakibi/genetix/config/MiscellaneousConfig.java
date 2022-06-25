package com.asakibi.genetix.config;

import me.lortseam.completeconfig.api.ConfigEntries;
import me.lortseam.completeconfig.api.ConfigGroup;

@ConfigEntries(includeAll = true)
public class MiscellaneousConfig implements ConfigGroup {

    @Override
    public String getId() {
        return "miscellaneous";
    }

}

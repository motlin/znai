package com.twosigma.testing.documentation

import com.twosigma.documentation.DocumentationArtifacts
import com.twosigma.testing.data.table.TableData
import org.junit.Test

class TestToDocExample {
    @Test
    void shouldRestrictAccountsActivity() {
        def rules = ["Account Type" | "Operation"    | "Restriction"] {
                    _________________________________________________
                        "SPB3"      | "Buy Options"  | "weekends only"
                        "TR"        | "Sell Futures" | "except holidays"
                        "BOSS"      | "Buy Stocks"   | "none" }

        validateRules(rules)
    }

    private static void validateRules(TableData rules) {
        DocumentationArtifacts.create(TestToDocExample,
                "account-rules.json", rules.toJson())
    }
}

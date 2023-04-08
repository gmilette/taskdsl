# taskdsl
Sample code to demonstrate building DSLs in Kotlin. Part of a pluralsite course.

structure based:
```
        val builder = CampaignBuilder()
        val campaign = builder {
            email("t1") {
                morning
                on("10-20-23")
            }
            call("t2") {
                "t1".after(5)
            }
            call("t3") {
                "t1" += 10
            }
            mail("t4") {
                by(Overnight)
                on("10-24-23")
            }
        }
```

infix
```
        val builder = CampaignBuilder()
        val campaign = builder {
            "t1" isA call at 5 on "10-22-23"
        }
```

function chains with symbols and context variables
```
        val campaign = CampaignBuilder()
            .emailWithin("c1", hourStart = 10, hourEnd = 16, date = "10-20-23")
            .callAfter("c2", hours = 24)
            .build()
```            

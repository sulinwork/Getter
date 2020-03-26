package com.sulin.use.bean;


import com.sulin.proess.annotition.*;

import java.util.List;


@Getter
@Method(instances = {
        @InstanceMethod(path = "item", targetField = "activityId"),
        @InstanceMethod(path = "action", targetField = "activityId")
}, collections = {
        @CollectionMethod(path = "context", targetField = "activityId")
})
public class AppBean {



}

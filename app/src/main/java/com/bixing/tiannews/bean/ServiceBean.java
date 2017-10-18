package com.bixing.tiannews.bean;

import java.util.List;

/**
 * Created by sjw on 2017/9/30.
 */

public class ServiceBean extends BaseBean {
    /**
     * title
     * child
     * serviceId
     * title
     * icon
     */
    private String title;
    private List<Child> child;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Child> getChild() {
        return child;
    }

    public void setChild(List<Child> child) {
        this.child = child;
    }

    public class Child {
        private String title;
        private String serviceId;
        private String icon;
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }
}

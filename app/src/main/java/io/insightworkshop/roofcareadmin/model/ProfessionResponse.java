package io.insightworkshop.roofcareadmin.model;

import java.util.List;

public class ProfessionResponse {
    private Boolean Success;
    private List<Professions> professions;

    public Boolean getSuccess() {
        return Success;
    }

    public void setSuccess(Boolean success) {
        Success = success;
    }

    public List<Professions> getProfessions() {
        return professions;
    }

    public void setProfessions(List<Professions> professions) {
        this.professions = professions;
    }

    public static class Professions {
        private int professionId;
        private String professionName;

        public int getProfessionId() {
            return professionId;
        }

        public void setProfessionId(int professionId) {
            this.professionId = professionId;
        }

        public String getProfessionName() {
            return professionName;
        }

        public void setProfessionName(String professionName) {
            this.professionName = professionName;
        }
    }
}


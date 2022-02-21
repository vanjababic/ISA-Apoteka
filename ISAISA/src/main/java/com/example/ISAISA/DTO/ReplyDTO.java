package com.example.ISAISA.DTO;

public class ReplyDTO {
    private String reply;
    private Integer complaintID;

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public Integer getComplaintID() {
        return complaintID;
    }

    public void setComplaintID(Integer complaintID) {
        this.complaintID = complaintID;
    }

    public ReplyDTO(String reply, Integer complaintID) {
        this.reply = reply;
        this.complaintID = complaintID;
    }

    public ReplyDTO() {
    }
}

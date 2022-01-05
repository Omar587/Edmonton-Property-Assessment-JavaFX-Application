package com._305.propertyassessment;

public class Neighbourhood {

   private String  neighbourhoodId;
   private String area;
   private String ward;

   public Neighbourhood(String neighbourhoodId, String area, String ward) {
      this.neighbourhoodId = neighbourhoodId;
      this.ward = ward;
      this.area = area;
   }

   public String getNeighbourhoodId() {
      return neighbourhoodId;
   }

   public void setNeighbourhoodId(String neighbourhoodId) {
      this.neighbourhoodId = neighbourhoodId;
   }

   public String getWard() {
      return ward;
   }

   public void setWard(String ward) {
      this.ward = ward;
   }

   public void setArea(String area) {
      this.area = area;
   }

   public String getArea() {
      return area;}


   @Override
   public String toString() {

      String neighbourhood= area + " " + ward;

      return neighbourhood;
   }
}

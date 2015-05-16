/***
 * Excerpted from "Modular Java",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/cwosg for more book information.
***/

package com.dudewheresmyjar.domain;


import org.compass.annotations.Index;
import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableProperty;
import org.compass.annotations.Store;

@Searchable(alias = "jar")

public class JarFile {

   @SearchableProperty(store = Store.YES, index = Index.UN_TOKENIZED)

   private String repository;

   @SearchableProperty(store = Store.YES, index = Index.UN_TOKENIZED)

   private String groupId;

   @SearchableProperty(store = Store.YES, index = Index.UN_TOKENIZED)

   private String artifactId;

   @SearchableProperty(store = Store.YES, index = Index.UN_TOKENIZED)

   private String version;
   private boolean snapshot;
   private boolean bundle;

   public String getRepository() {
      return repository;
   }

   public void setRepository(String repository) {
      this.repository = repository;
   }

   public String getGroupId() {
      return groupId;
   }

   public void setGroupId(String groupId) {
      this.groupId = groupId;
   }

   public String getArtifactId() {
      return artifactId;
   }

   public void setArtifactId(String artifactId) {
      this.artifactId = artifactId;
   }

   public String getVersion() {
      return version;
   }

   public void setVersion(String version) {
      this.version = version;
   }

   public boolean isSnapshot() {
      return snapshot;
   }

   public void setSnapshot(boolean snapshot) {
      this.snapshot = snapshot;
   }

   public boolean isBundle() {
      return bundle;
   }

   public void setBundle(boolean bundle) {
      this.bundle = bundle;
   }


   @SearchableId

   public String getFullyQualifiedArtifactUrl() {
      return "mvn:" + repository + ":" + groupId + "/" + artifactId + "/"
                        + version;
   }
}


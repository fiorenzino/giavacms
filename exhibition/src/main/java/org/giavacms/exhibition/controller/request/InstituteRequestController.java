package org.giavacms.exhibition.controller.request;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.giavacms.common.annotation.OwnRepository;
import org.giavacms.common.controller.AbstractRequestController;
import org.giavacms.common.model.Search;
import org.giavacms.exhibition.model.Institute;
import org.giavacms.exhibition.model.Participant;
import org.giavacms.exhibition.model.Testimonial;
import org.giavacms.exhibition.repository.InstituteRepository;
import org.giavacms.exhibition.service.ParticipantService;

@Named
@RequestScoped
public class InstituteRequestController extends AbstractRequestController<Institute>
         implements Serializable
{

   private static final long serialVersionUID = 1L;

   public static final String SEARCH = "q";
   public static final String[] PARAM_NAMES = new String[] { SEARCH };
   public static final String ID_PARAM = "institute";
   public static final String EXHIBITION = "exhibition";
   public static final String CURRENT_PAGE_PARAM = "start";

   @Inject
   @OwnRepository(InstituteRepository.class)
   InstituteRepository instituteRepository;

   @Inject
   ParticipantService participantService;

   public InstituteRequestController()
   {
      super();
   }

   @Override
   protected void init()
   {
      super.init();
   }

   @Override
   public List<Institute> loadPage(int startRow, int pageSize)
   {
      Search<Participant> r = new Search<Participant>(Participant.class);
      r.getObj().getSubject().setType(Institute.TYPE);
      r.getObj().getExhibition().setId(getParams().get(EXHIBITION));
      r.getObj().getSubject().setSurname(getParams().get(SEARCH));
      return (List<Institute>) participantService.getList(r, startRow, pageSize);
   }

   @Override
   public int totalSize()
   {
      // siamo all'interno della stessa richiesta per servire la quale è
      // avvenuta la postconstruct
      Search<Participant> r = new Search<Participant>(Participant.class);
      r.getObj().getSubject().setType(Testimonial.TYPE);
      r.getObj().getExhibition().setId(getParams().get(EXHIBITION));
      r.getObj().getSubject().setSurname(getParams().get(SEARCH));
      return participantService.getListSize(r);
   }

   @Override
   public String[] getParamNames()
   {
      return PARAM_NAMES;
   }

   @Override
   public String getIdParam()
   {
      return ID_PARAM;
   }

   @Override
   public String getCurrentPageParam()
   {
      return CURRENT_PAGE_PARAM;
   }

   public boolean isScheda()
   {
      return getElement() != null && getElement().getId() != null;
   }

   public String viewElement(String id)
   {
      setElement(instituteRepository.fetch(id));
      return viewPage();
   }

}

package org.giavacms.faq.controller;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.giavacms.base.common.util.FileUtils;
import org.giavacms.base.model.attachment.Image;
import org.giavacms.common.annotation.BackPage;
import org.giavacms.common.annotation.EditPage;
import org.giavacms.common.annotation.ListPage;
import org.giavacms.common.annotation.OwnRepository;
import org.giavacms.common.annotation.ViewPage;
import org.giavacms.common.controller.AbstractLazyController;
import org.giavacms.faq.model.FaqCategory;
import org.giavacms.faq.producer.FaqProducer;
import org.giavacms.faq.repository.FaqCategoryRepository;

@Named
@SessionScoped
public class FaqCategoryController extends AbstractLazyController<FaqCategory> {

	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------
	@BackPage
	public static String BACK = "/private/administration.xhtml";
	@ViewPage
	public static String VIEW = "/private/faq/faqcategory/view.xhtml";
	@ListPage
	public static String LIST = "/private/faq/faqcategory/list.xhtml";
	@EditPage
	public static String EDIT = "/private/faq/faqcategory/edit.xhtml";

	public static String EDIT_IMAGE = "/private/faq/faqcategory/edit-image.xhtml";

	// ------------------------------------------------

	@Inject
	@OwnRepository(FaqCategoryRepository.class)
	FaqCategoryRepository faqCategoryRepository;

	@Inject
	FaqProducer faqProducer;

	@Override
	public void initController() {
	}

	@Override
	public String update() {
		saveImage();
		return super.update();
	}

	@Override
	public String save() {
		saveImage();
		faqProducer.reset();
		return super.save();
	}

	@Override
	public String delete() {
		super.delete();
		faqProducer.reset();
		return listPage();
	}

	public String deleteImg() {
		getElement().setImage(null);
		faqCategoryRepository.update(getElement());
		return listPage();
	}

	public String modImage() {
		// TODO Auto-generated method stub
		super.modElement();
		return EDIT_IMAGE + super.REDIRECT_PARAM;
	}

	public String modImageCurrent() {
		// TODO Auto-generated method stub
		super.modCurrent();
		return EDIT_IMAGE + super.REDIRECT_PARAM;
	}

	private void saveImage() {
		if (getElement().getNewImage().getUploadedData() != null
				&& getElement().getNewImage().getUploadedData().getContents() != null
				&& getElement().getNewImage().getUploadedData().getFileName() != null
				&& !getElement().getNewImage().getUploadedData().getFileName()
						.isEmpty()) {
			logger.info("carico nuova immagine: "
					+ getElement().getNewImage().getUploadedData()
							.getFileName());
			Image img = new Image();
			img.setData(getElement().getNewImage().getUploadedData()
					.getContents());
			img.setType(getElement().getNewImage().getUploadedData()
					.getContentType());
         String filename = FileUtils.createImage_("img", getElement()
					.getNewImage().getUploadedData().getFileName(),
					getElement().getNewImage().getUploadedData().getContents());
			img.setFilename(filename);
			getElement().setImage(img);
			getElement().setNewImage(null);
		} else {
			logger.info("non c'e' nuova immagine");
		}
	}

	@Override
	public Object getId(FaqCategory t) {
		// TODO Auto-generated method stub
		return t.getId();
	}

	@Override
	public String reset() {
		faqProducer.reset();
		return super.reset();
	}
}

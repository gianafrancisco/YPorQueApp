
package yporque.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import yporque.model.Articulo;
import yporque.model.Product;
import yporque.repository.ArticuloRepository;
import yporque.repository.ProductRepository;

@SpringUI(path = "/")
@Theme("valo")
public class ArticuloUI extends UI {

	private final ArticuloRepository articuloRepository;
	private final ArticuloEditor editor;

	private final Grid grid;

	private final TextField filter;

	private final Button addNewBtn;

	@Autowired
	public ArticuloUI(ArticuloRepository articuloRepository, ArticuloEditor editor) {
		this.editor = editor;
		this.articuloRepository = articuloRepository;
		this.grid = new Grid();
		this.filter = new TextField();
		this.addNewBtn = new Button("Nuevo Producto", FontAwesome.PLUS);
	}

	@Override
	protected void init(VaadinRequest request) {

		Articulo art = new Articulo(1.0,1.0,1.0,1.0,1.0,"articulo1");
		articuloRepository.save(art);

		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		HorizontalLayout secondaryLayout = new HorizontalLayout(grid, editor);
		secondaryLayout.setSpacing(true);
		VerticalLayout mainLayout = new VerticalLayout(actions, secondaryLayout);
		mainLayout.setWidth(100,Unit.PERCENTAGE);
		setContent(mainLayout);
		setWidth(100,Unit.PERCENTAGE);

		// Configure layouts and components
		actions.setSpacing(true);
		mainLayout.setMargin(true);
		mainLayout.setSpacing(true);

		grid.setHeight(100, Unit.PERCENTAGE);
		//grid.setColumns("id", "description", "code", "stock", "price");

		filter.setInputPrompt("filtrar por código");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.addTextChangeListener(e -> listProducts(e.getText()));
		filter.setWidth(300,Unit.PIXELS);

		// Connect selected Customer to editor or hide if none is selected
		grid.addSelectionListener(e -> {
			if (e.getSelected().isEmpty()) {
				editor.setVisible(false);
			}
			else {
				editor.edit((Articulo) e.getSelected().iterator().next());
			}
		});

		// Instantiate and edit new Customer the new button is clicked
		addNewBtn.addClickListener(e -> editor.edit(new Articulo(1.0,1.0,1.0,1.0,1.0,"articulo1")));

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listProducts(filter.getValue());
		});

		listProducts(null);
	}

	// tag::listProducts[]
	private void listProducts(String text) {
		if (StringUtils.isEmpty(text)) {
			//grid.setContainerDataSource(
					//new BeanItemContainer(Articulo.class, articuloRepository.findAll().iterator()));
		}
		else {
			grid.setContainerDataSource(new BeanItemContainer(Articulo.class,
					articuloRepository.findByDescripcionStartsWithIgnoreCase(text)));
		}
	}
	// end::listProducts[]

}

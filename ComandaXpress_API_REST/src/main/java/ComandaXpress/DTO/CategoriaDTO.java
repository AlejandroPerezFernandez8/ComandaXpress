package ComandaXpress.DTO;

import ComandaXpress.Categoria.Model.Categoria;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class CategoriaDTO {
    private Long categoriaId;
    private String nombre;
    private String descripcion;

    public static CategoriaDTO converter(Categoria source){
        CategoriaDTO target = new CategoriaDTO();
        target.setCategoriaId(source.getCategoriaId());
        target.setNombre(source.getNombre());
        target.setDescripcion(source.getDescripcion());
        return target;
    }
}

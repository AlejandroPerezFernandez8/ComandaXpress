package ComandaXpress.DTO;

import ComandaXpress.Categoria.Model.Categoria;
import ComandaXpress.Producto.Model.Producto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class ProductoDTO {
    private Long productoId;
    private String nombre;
    private BigDecimal precio;
    private String descripcion;
    private String imagenUrl;
    private boolean activo;
    public Long categoriaId;

    public static ProductoDTO converter(Producto source){
        ProductoDTO target = new ProductoDTO();
        target.setProductoId(source.getProductoId());
        target.setNombre(source.getNombre());
        target.setPrecio(source.getPrecio());
        target.setDescripcion(source.getDescripcion());
        target.setImagenUrl(source.getImagenUrl());
        target.setActivo(source.isActivo());
        target.setCategoriaId(source.getCategoria().getCategoriaId());
        return target;
    }
}

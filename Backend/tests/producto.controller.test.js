jest.mock('../models/Producto');

const Producto = require('../models/Producto');
const controller = require('../controllers/producto.controller');

function crearRespuesta() {
  return {
    status: jest.fn().mockReturnThis(),
    json: jest.fn().mockReturnThis()
  };
}

describe('Controlador de productos', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  test('obtiene la lista de productos', async () => {
    const productos = [{ nombre: 'Laptop' }];
    const populate = jest.fn().mockResolvedValue(productos);

    Producto.find.mockReturnValue({ populate });

    const res = crearRespuesta();

    await controller.obtenerProductos({}, res);

    expect(Producto.find).toHaveBeenCalledTimes(1);
    expect(populate).toHaveBeenCalledWith('categoria', 'nombre');
    expect(res.json).toHaveBeenCalledWith(productos);
  });

  test('responde 500 cuando no puede obtener productos', async () => {
    const error = new Error('Error de base de datos');

    Producto.find.mockReturnValue({
      populate: jest.fn().mockRejectedValue(error)
    });

    const res = crearRespuesta();

    await controller.obtenerProductos({}, res);

    expect(res.status).toHaveBeenCalledWith(500);
  });

  test('crea un producto valido', async () => {
    const productoGuardado = {
      _id: '1',
      nombre: 'Monitor'
    };

    const save = jest.fn().mockResolvedValue(productoGuardado);

    Producto.mockImplementation(() => ({ save }));

    const req = {
      body: {
        nombre: 'Monitor',
        precio: 100000
      }
    };

    const res = crearRespuesta();

    await controller.crearProducto(req, res);

    expect(Producto).toHaveBeenCalledWith(req.body);
    expect(save).toHaveBeenCalledTimes(1);
    expect(res.status).toHaveBeenCalledWith(201);
    expect(res.json).toHaveBeenCalledWith(productoGuardado);
  });

  test('responde 400 cuando el producto es invalido', async () => {
    Producto.mockImplementation(() => ({
      save: jest.fn().mockRejectedValue(new Error('Datos invalidos'))
    }));

    const res = crearRespuesta();

    await controller.crearProducto({ body: {} }, res);

    expect(res.status).toHaveBeenCalledWith(400);
  });

  test('actualiza un producto existente', async () => {
    const actualizado = {
      _id: '1',
      nombre: 'Monitor actualizado'
    };

    Producto.findByIdAndUpdate.mockResolvedValue(actualizado);

    const req = {
      params: { id: '1' },
      body: { nombre: 'Monitor actualizado' }
    };

    const res = crearRespuesta();

    await controller.actualizarProducto(req, res);

    expect(Producto.findByIdAndUpdate).toHaveBeenCalledWith(
      '1',
      req.body,
      { new: true }
    );

    expect(res.json).toHaveBeenCalledWith(actualizado);
  });

  test('responde 404 cuando el producto a actualizar no existe', async () => {
    Producto.findByIdAndUpdate.mockResolvedValue(null);

    const res = crearRespuesta();

    await controller.actualizarProducto(
      {
        params: { id: '99' },
        body: {}
      },
      res
    );

    expect(res.status).toHaveBeenCalledWith(404);
  });

  test('elimina un producto existente', async () => {
    Producto.findByIdAndDelete.mockResolvedValue({ _id: '1' });

    const res = crearRespuesta();

    await controller.eliminarProducto(
      { params: { id: '1' } },
      res
    );

    expect(Producto.findByIdAndDelete).toHaveBeenCalledWith('1');
    expect(res.json).toHaveBeenCalledWith({
      mensaje: 'Producto eliminado'
    });
  });

  test('responde 404 cuando el producto a eliminar no existe', async () => {
    Producto.findByIdAndDelete.mockResolvedValue(null);

    const res = crearRespuesta();

    await controller.eliminarProducto(
      { params: { id: '99' } },
      res
    );

    expect(res.status).toHaveBeenCalledWith(404);
  });
});
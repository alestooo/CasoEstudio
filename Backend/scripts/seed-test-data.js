const mongoose = require('mongoose');
const dotenv = require('dotenv');

const Categoria = require('../models/Categoria');
const Producto = require('../models/Producto');
const Usuario = require('../models/Usuario');

dotenv.config();

const ids = {
  categoria: '64b000000000000000000001',
  producto: '64b000000000000000000002',
  usuario: '64b000000000000000000003'
};

async function prepararDatos() {
  if (!process.env.MONGO_URI) {
    throw new Error('La variable MONGO_URI es obligatoria');
  }

  await mongoose.connect(process.env.MONGO_URI);

  await Categoria.findByIdAndUpdate(
    ids.categoria,
    {
      nombre: 'Tecnologia CI',
      descripcion: 'Categoria utilizada por las pruebas automaticas'
    },
    {
      upsert: true,
      new: true,
      setDefaultsOnInsert: true
    }
  );

  await Producto.findByIdAndUpdate(
    ids.producto,
    {
      nombre: 'Producto CI',
      precio: 350000,
      descripcion: 'Producto utilizado por las pruebas automaticas',
      imagen: 'producto-ci.jpg',
      categoria: ids.categoria,
      stock: 10
    },
    {
      upsert: true,
      new: true,
      setDefaultsOnInsert: true
    }
  );

  await Usuario.findByIdAndUpdate(
    ids.usuario,
    {
      nombre: 'Usuario CI',
      correo: 'usuario-ci@prueba.com',
      password: 'Hola123',
      rol: 'cliente'
    },
    {
      upsert: true,
      new: true,
      setDefaultsOnInsert: true
    }
  );

  console.log('Datos de prueba preparados correctamente');

  await mongoose.disconnect();
}

prepararDatos().catch(async (error) => {
  console.error(
    'No se pudieron preparar los datos de prueba:',
    error.message
  );

  await mongoose.disconnect();
  process.exit(1);
});
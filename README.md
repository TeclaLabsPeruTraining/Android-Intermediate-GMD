# Android-Intermediate-GMD
Android Intermediate (23h)

## Lección 7 Multimedia

  - Derivando funcionalidad a otras aplicaciones (Cámara de foto, agenda de contactos, email, Lector de códigos QR, etc)

## Invocando a la galería de imágenes y la cámara

Para esto usamos los intent, ya se para llamar a la cámara o a la galería de imágenes del dispositivo

Galería del dispositivo :
```java
   private void gotoPhoto() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        this.startActivityForResult(galleryIntent, ACTION_GALLERY_PHOTO);
    }
```

Cámara del dispositivo :

```java
    private void gotoCamera() {
        boolean cameraAvailable= intentHelper.isIntentAvailable(this, MediaStore.ACTION_IMAGE_CAPTURE);
        if(!cameraAvailable)return;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File f = null;
        try {
            f = setUpPhotoFile();
            currentPhotoPath = f.getAbsolutePath();
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        } catch (IOException e) {
            e.printStackTrace();
            f = null;
            currentPhotoPath = null;
        }
        startActivityForResult(takePictureIntent, ACTION_TAKE_PHOTO);
    }
```

Luego de seleccionar una foto o tomar una foto con la cámara , recibimos la respuesta :
```java

  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ACTION_GALLERY_PHOTO: {
                if (resultCode == RESULT_OK) {
                    if(data!=null && data.getData()!=null){
                        processPhotoGallery(data.getData());
                    }
                }
                break;
            }

            case ACTION_TAKE_PHOTO: {
                if (resultCode == RESULT_OK) {
                    processPhoto();
                }
                break;
            }
        }
    }
    
```

* Sucede alguna  veces y en algunos dispositivos que cuando recibimos la respuesta no llega la foto, paraevitar esto,  previamente en la llamada enviamos un file donde esperamos se almacene la foto y luega poder acceder a esta.

Procesar las imagenes :

Sucede que la imágen que deseamos cargar es muy grande , hay un limite para esto de 2048x2048  píxeles , para lo cual antes de cargar la imágen y mostrarla , debemos escalarla en proporción. Tambien sucede que la imágen no este en la orientación correcta, para lo cual debemos ajustar la orientación.

Escalar una imágen
```java

  public Bitmap bitmapByPath(int imageWidth,int imageHeight,String path){
        /* There isn't enough memory to open up more than a couple camera photos */
		/* So pre-scale the target bitmap into which the file is decoded */

		/* Get the size of the ImageView */
        int targetW = imageWidth;
        int targetH = imageHeight;

		/* Get the size of the image */
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

		/* Figure out which way needs to be reduced less */
        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        }

		/* Set bitmap options to scale the image decode target */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
        Bitmap originalBitmap = BitmapFactory.decodeFile(path, bmOptions);
        Bitmap bitmap= fixOrientationBitmap(path,originalBitmap);
        if(originalBitmap.isRecycled()){
            originalBitmap.recycle();
            originalBitmap=null;
        }
        return bitmap;
    }
```
Ajustar orientación :

```java

    private Bitmap fixOrientationBitmap(String path, Bitmap bitmap){
        Matrix matrix= new Matrix();
        matrix.postRotate(angleByPath(path));

        Bitmap mBitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        return  mBitmap;
    }

    private int angleByPath(String path){
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(path);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        String tagOrientation = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
        int orientation = tagOrientation != null ? Integer.parseInt(tagOrientation) :  ExifInterface.ORIENTATION_NORMAL;

        int degree = 0;
        switch (orientation){

            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                degree=0; //scale -1, 1
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                degree=180; //scale -1, 1
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                degree=90; //scale -1, 1
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                degree=90; //-1,1
                break;
            case ExifInterface.ORIENTATION_NORMAL:
                degree=0;
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                degree= 90;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                degree=180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                degree=270; //-90
                break;
        }
        return degree;
    }
```
## Referencias 

- Camera API [https://developer.android.com/guide/topics/media/camera.html](https://developer.android.com/guide/topics/media/camera.html)

- Take Photo [https://developer.android.com/training/camera/photobasics.html](https://developer.android.com/training/camera/photobasics.html)

- Camera [https://developer.android.com/training/camera/cameradirect.html](https://developer.android.com/training/camera/cameradirect.html)

- Procesando Bitmaps [https://developer.android.com/topic/performance/graphics/load-bitmap.html](https://developer.android.com/topic/performance/graphics/load-bitmap.html)


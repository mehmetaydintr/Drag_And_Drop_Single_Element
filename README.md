# Drag And Drop Single Element

## İçerik

1. [Kullanılan Teknolojiler](https://github.com/mehmetaydintr/Drag_And_Drop_Single_Element/blob/main/README.md#kullan%C4%B1lan-teknolojiler)
2. [Proje Tanımı](https://github.com/mehmetaydintr/Drag_And_Drop_Single_Element/blob/main/README.md#proje-tan%C4%B1m%C4%B1)
3. [Kod Tanımı](https://github.com/mehmetaydintr/Drag_And_Drop_Single_Element/blob/main/README.md#kod-tan%C4%B1m%C4%B1)
4. [Örnek Ekran Görüntüleri](https://github.com/mehmetaydintr/Drag_And_Drop_Single_Element/blob/main/README.md#%C3%B6rnek-ekran-g%C3%B6r%C3%BCnt%C3%BCleri)


## Kullanılan Teknolojiler

  + Android Studio

![Image of Android Studio](https://www.xda-developers.com/files/2017/04/android-studio-logo.png)

  + Java

![Image of Java](https://yazilimamelesi.files.wordpress.com/2013/03/java_logo.jpg)


## Proje Tanımı

Bu proje **Drag & Drop** özelliğini kullanıdğım bir Android Mobil Uygulamasıdır. Drag & Drop hayatımızdaki bir çok uygulamalarda bulunmaktadır. Drag & Drop bir ekran nesnesini (ikon) seçip işaretleyerek, bir başka ekran nesnesinin içine koymak anlamında kullanılan bir GUI deyimi. Yani sürükle bırak tekniğidir. Drag & Drop hem uygulamaya görsel olarak güzellik katarken aynı zamanda kullanıcı deneyimini geliştirmektedir. Kullanıcıların ilgisini çekmektedir.

## Kod Tanımı

+ İlk olarak tasarım ile başlayalım. Basit bir tasarım yapacağız. Sadece bir adet `Relative Layout` ve bir adet `Button` yerleştirelim.

![1](https://user-images.githubusercontent.com/37263322/118358458-7ef30400-b587-11eb-90a6-def814dde5fd.png)

+ Şimdi Java kodlarına geçebiliriz. İlk olarak gerekli tanımlamalarımızı yapıyoruz. Buttonumuzu daha kolay tanımlayabilmek için etiket oluşturuyoruz `BUTTON_ETIKET`. Relative Layout'u yeniden yapılandırmamız gerekecek bu yüzden `RelativeLayout.LayoutParams` oluşturuyoruz.

```
private Button button;
private RelativeLayout relativeLayout;

private static final String BUTTON_ETIKET = "DRAG BUTTON";

private RelativeLayout.LayoutParams layoutParams;
```

+ Buttonumuza oluşturduğumuz etiketi `main` metodu içerisinde ekliyoruz.

```
button.setTag(BUTTON_ETIKET);
```

+ Drag & Drop işlemimizi tetiklemek için **Button**umuza `setOnLongClickListener` ekleyeceğiz ve gerekli kodlamaları yapacağız. `ClipData` android işletim sisteminde bir şeyleri (metin, resim, emoji, veya tasarım öğesi vb.) kopyalamak için kullanılıyor. Bizde buttonumuzu kopyalayıp daha sonra bırakıldığı yere yapıştıracağız. Kısaca aşağıdaki kod bloğu buttonu kopyalayıp **visibitily** özelliğini invisible yapıyor.

```
button.setOnLongClickListener(new View.OnLongClickListener() {
    @Override
    public boolean onLongClick(View view) {

        ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());

        String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};

        ClipData clipData = new ClipData(view.getTag().toString(), mimeTypes, item);

        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);

        view.startDrag(clipData, shadowBuilder,view, 0);

        view.setVisibility(View.INVISIBLE);

        return true;
    }
});
```

+ Daha sonra **Relative Layout**umuza  Drag & Drop işlemlerini takip edebilmesi için `setOnDragListener` ekliyoruz. setOnDragListener kendine özgü actionlar barındırmaktadır. `switch case` yapısı kullanarak bu actionlardan faydalanacağız.

| Action | İşlevi |
|    :---:     |     :---       |
| **DragEvent.ACTION_DRAG_STARTED** | Drag & Drop işlemi başladığı zaman tetiklenir. |
| **DragEvent.ACTION_DRAG_ENTERED** | Görsel nesne belirli bir alana veya layouta girdiği zaman tetiklenir. |
| **DragEvent.ACTION_DRAG_EXITED** | Görsel nesne belirli bir alandan veya layoutan çıktığı zaman tetiklenir. |
| **DragEvent.ACTION_DRAG_LOCATION** | Görsel nesne konumlandırıldığı zaman tetiklenir. |
| **DragEvent.ACTION_DRAG_ENDED** | Drag & Drop işlemi bittiği zaman tetiklenir. |
| **DragEvent.ACTION_DROP** | Görsel nesne bırakıldığı zaman tetiklenir. |

Drag & Drop işlemi başladığı zaman görsel nesnemizin bilgilerini işlem bittiği zaman kullanmak için `layoutParams` nesnesine atıyoruz. Görsel nesne bırakıldığı zaman ilk olarak görsel nesnenin yeni koordinatlarını `layoutParams` nesnesi üzerinden güncelliyoruz. Yeni bir görsel nesne oluşturuyoruz (Görsel nesne herhangi formatta olabileceği için `View` olarak oluşturuyoruz). Layout'umuzun türü herhangi bir formda olabileceği için `ViewGroup` kullanarak mevcut layoutumuzu tanımlıyoruz ve görsel nesnemizi bu layout içerisinden siliyoruz. Daha sonra yeni bir layout oluşturuyoruz ve içerisine görsel nesnemizi **layoutParams** ile birlikte ekleyip **visibility** özelliğini visible yapıyoruz.

```
relativeLayout.setOnDragListener(new View.OnDragListener() {
    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {

        switch (dragEvent.getAction()){
            case DragEvent.ACTION_DRAG_STARTED:
                layoutParams = (RelativeLayout.LayoutParams) button.getLayoutParams();  //Button bilgilerini atama
                Log.e("sonuç", "ACTION_DRAG_STARTED");
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                Log.e("sonuç", "ACTION_DRAG_ENTERED");
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                Log.e("sonuç", "ACTION_DRAG_EXITED");
                break;
            case DragEvent.ACTION_DRAG_LOCATION:
                Log.e("sonuç", "ACTION_DRAG_LOCATION");
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                Log.e("sonuç", "ACTION_DRAG_ENDED");
                break;
            case DragEvent.ACTION_DROP:
                
                //Buttonun koordinatlarını güncelleme
                layoutParams.leftMargin = (int) dragEvent.getX();
                layoutParams.topMargin = (int) dragEvent.getY();

                //Buttonumuzu ve mevcut layoutumuzu tanımlayıp buttounumuzu layoutumuz içerisinden silme
                View gorselNesne = (View) dragEvent.getLocalState();
                ViewGroup eskiTasarimAlani = (ViewGroup) gorselNesne.getParent();
                eskiTasarimAlani.removeView(gorselNesne);
                
                //Yeni bir layout nesnesi tanımla ve buttonumuzu layoutParams ile ekleme
                RelativeLayout hedefTasarimAlani = (RelativeLayout) view;
                hedefTasarimAlani.addView(gorselNesne, layoutParams);

                gorselNesne.setVisibility(View.VISIBLE);

                Log.e("sonuç", "ACTION_DROP");
                break;
            default:
                break;
        }

        return true;
    }
});
```

## Örnek Ekran Görüntüleri

<img src="https://user-images.githubusercontent.com/37263322/118359664-2e7ea500-b58d-11eb-9c26-10582af6fd47.gif" width="300">


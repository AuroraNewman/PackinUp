import Cropper from "react-easy-crop";
import helihike from './assets/helihike.jpeg';

const ImageCropper = ({ imageSrc }) => {
  return (
    <div style={{ position: "relative", width: "100%", height: 300 }}>
      <Cropper image={helihike} aspect={4 / 3} />
    </div>
  );
};
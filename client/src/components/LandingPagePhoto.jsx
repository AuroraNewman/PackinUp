import Cropper from "react-easy-crop";
import helihike from './assets/helihike.jpeg';

const ImageCropper = ({ imageSrc }) => {
  return (
    <div className="image-cropper-container">
      <Cropper image={helihike} aspect={4 / 3} />
    </div>
  );
};
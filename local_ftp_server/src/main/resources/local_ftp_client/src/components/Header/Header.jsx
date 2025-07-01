import styles from "./Header.module.css";
import Button from "../Button/Button";

function Header() {
  const element = (
    <header>
        <h1>File sharing</h1>
        <Button iconName="settings" variant="filled" />
    </header>
  );

  return element;
}

export default Header;

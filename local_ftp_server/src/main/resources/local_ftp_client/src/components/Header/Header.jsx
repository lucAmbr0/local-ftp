import styles from "./Header.module.css";
import Button from "../Button/Button";

function Header() {
  const element = (
    <div className={styles.container}>
        <h1 className={styles.title}>File sharing</h1>
        <Button iconName="settings" variant="filled" />
    </div>
  );

  return element;
}

export default Header;
